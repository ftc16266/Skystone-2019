package org.firstinspires.ftc.teamcode.testing

import android.util.Log
import com.acmerobotics.roadrunner.drive.DriveSignal
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.TrajectoryMarker
import com.acmerobotics.roadrunner.util.NanoClock
import java.lang.Math.abs

abstract class TrajectoryFollowerEx @JvmOverloads constructor(
    private val admissibleError: Pose2d = Pose2d(),
    private val timeout: Double = 0.0,
    protected val clock: NanoClock = NanoClock.system()
) {
    private var startTimestamp: Double = 0.0
    private var admissible = false
    private var remainingMarkers = mutableListOf<TrajectoryMarker>()
    private var executedFinalUpdate = false

    /**
     * Trajectory being followed if [isFollowing] is true.
     */
    lateinit var trajectory: Trajectory
        protected set

    /**
     * Robot pose error computed in the last [update] call.
     */
    abstract var lastError: Pose2d
        protected set

    /**
     * Follow the given [trajectory].
     */
    open fun followTrajectory(trajectory: Trajectory) {
        this.startTimestamp = clock.seconds()
        this.trajectory = trajectory
        this.admissible = false

        remainingMarkers.clear()
        remainingMarkers.addAll(trajectory.markers)
        remainingMarkers.sortBy { it.time }

        executedFinalUpdate = false
    }

    private fun internalIsFollowing(): Boolean {
        val timeRemaining = trajectory.duration() - elapsedTime()
        return timeRemaining > 0 || (!admissible && timeRemaining > -timeout)
    }

    /**
     * Returns true if the current trajectory has finished executing.
     */
    fun isFollowing() = !executedFinalUpdate || internalIsFollowing()

    fun isDone() = executedFinalUpdate && !internalIsFollowing()

    /**
     * Returns the elapsed time since the last [followTrajectory] call.
     */
    fun elapsedTime() = clock.seconds() - startTimestamp

    /**
     * Run a single iteration of the trajectory follower.
     *
     * @param currentPose current robot pose
     */
    fun update(currentPose: Pose2d): DriveSignal {
        while (remainingMarkers.size > 0 && elapsedTime() > remainingMarkers[0].time) {
            remainingMarkers.removeAt(0).callback.onMarkerReached()
        }

        val trajEndError = trajectory.end() - currentPose
        admissible = abs(trajEndError.x) < admissibleError.x &&
                abs(trajEndError.y) < admissibleError.y &&
                abs(trajEndError.heading) < admissibleError.heading

        return if (internalIsFollowing() || executedFinalUpdate) {
            internalUpdate(currentPose)
        } else {
            for (marker in remainingMarkers) {
                marker.callback.onMarkerReached()
            }
            remainingMarkers.clear()
            executedFinalUpdate = true
            DriveSignal()
        }
    }

    protected abstract fun internalUpdate(currentPose: Pose2d): DriveSignal
}
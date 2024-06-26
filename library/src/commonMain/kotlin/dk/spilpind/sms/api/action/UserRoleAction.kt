package dk.spilpind.sms.api.action

import dk.spilpind.sms.api.common.Action
import dk.spilpind.sms.core.model.Context
import kotlinx.serialization.Serializable

/**
 * All possible actions that can be made in relation to [Context.UserRole]
 */
@Serializable
sealed class UserRoleAction : ContextAction() {
    override val context: Context = Context.UserRole

    /**
     * Adds a new user role. A successful response to this would be [UserRoleReaction.Added]
     */
    @Serializable
    data class Add(
        val userId: Int,
        val roleContext: String,
        val contextId: Int,
        val role: String,
        val isPublic: Boolean
    ) : UserRoleAction() {
        override val action: Action = Action.Add
    }

    /**
     * Subscribes to all user roles associated with the user identified by [userId] if available for the current user.
     * The subscription will be kept alive until the session is destroyed or [Unsubscribe] is called. Changes to the
     * list will be sent via relevant [UserRoleReaction]s. A successful response to this would be
     * [UserRoleReaction.Subscribed] followed by [UserRoleReaction.Updated]
     */
    @Serializable
    data class Subscribe(val userId: Int) : UserRoleAction() {
        override val action: Action = Action.Subscribe
    }

    /**
     * Unsubscribes the socket from updates to the list of user roles, previously subscribed by [Subscribe]. A
     * successful response to this would be [UserRoleReaction.Unsubscribed]
     */
    @Serializable
    data class Unsubscribe(val userId: Int) : UserRoleAction() {
        override val action: Action = Action.Unsubscribe
    }
}

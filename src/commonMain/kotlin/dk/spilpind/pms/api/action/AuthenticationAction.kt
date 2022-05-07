package dk.spilpind.pms.api.action

import dk.spilpind.pms.api.common.Action
import dk.spilpind.pms.api.common.Context
import kotlinx.serialization.Serializable

/**
 * All possible actions that can be made in relation to [Context.Authentication]
 */
@Serializable
sealed class AuthenticationAction : ContextAction() {
    override val context: Context = Context.Authentication

    /**
     * Request to get information about how to authenticate, where [redirectUrl] should be the URL the user should be
     * redirected to after authentication. A successful response to this would be [AuthenticationReaction.Informed]
     */
    @Serializable
    data class Inform(val redirectUrl: String) : AuthenticationAction() {
        override val action: Action = Action.Inform
        override val minimumAccessLevel: Int? = null
    }

    /**
     * Request to authenticate the user (i.e. add it to the current connection). It is required to pass in either
     * [googleToken] or [googleCode], where [GoogleCode.redirectUrl] should be the same as for [Inform]. A successful
     * response to this would be [AuthenticationReaction.Added]
     */
    @Serializable
    data class Add(
        val googleToken: String? = null,
        val googleCode: GoogleCode? = null
    ) : AuthenticationAction() {
        override val action: Action = Action.Add
        override val minimumAccessLevel: Int? = null

        @Serializable
        data class GoogleCode(val code: String, val redirectUrl: String)
    }
}

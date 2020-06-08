package tk.alexlopez.sallefy.utils

class AuthenticationHeader {

    companion object {
        val instance = AuthenticationHeader()
    }

    val session = Session.instance()

    val token: String
            get() = "Bearer ${session.userToken.idToken}"
}
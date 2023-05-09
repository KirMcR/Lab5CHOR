package com.ru.kirmcr.lab5chor

import android.text.BoringLayout
import androidx.lifecycle.ViewModel

class UserListViewModel : ViewModel() {
    var users = mutableListOf<User>()

    init {
        users += User().apply {
            this.login = "kirill101"
            this.password = "w7654321"
        }

        users += User().apply {
            this.login = "ktototam"
            this.password = "p1234567"
        }
    }

    var userError: User = User().apply {
        this.login = "ktototam"
        this.password = "p1234567"
    }
    var repeatPassword: String = ""
    var textError: Int = R.string.empty
    var textRegError: Int = R.string.empty
    var testUser: User = User()
    fun getUser(): User {
        return users[0]
    }

    fun addUser(login: String, password: String) {
        users += User().apply {
            this.login = login
            this.password = password
        }
    }

    fun userWithComprasion(): Boolean {
        if (testUser.password.isBlank() && testUser.login.isBlank()) {
            textError = R.string.field_emp
            return false
        } else if (!(testUser.login.matches(Regex("[a-zA-Z0-9]+")) || !(testUser.password.matches(
                Regex("[a-zA-Z0-9]+")
            )))
        ) {
            textError = R.string.wrong_char
            return false
        } else if (testUser.password.isBlank()) {
            textError = R.string.pas_emp
            return false
        } else if (testUser.login.isBlank()) {
            textError = R.string.log_emp
            return false
        } else if (!checkLogins()) {
            textError = R.string.wrong_user
            return false
        } else if (!checkPass()) {
            textError = R.string.wrong_passw
            return false
        } else if (testUser.login == userError.login && testUser.password == userError.password) {
            textError = R.string.server_error
            return false
        }
//        for (i in users){
//            if(usersComprasion(i)){
//                return true
//            }
//        }
        else return true
    }

    fun userRegComprasion(): Boolean {

        if (testUser.password.isBlank() && testUser.login.isBlank() && repeatPassword.isBlank()) {
            textRegError = R.string.field_emp
            return false
        }  else if (testUser.password.isBlank()) {
            textRegError = R.string.pas_emp
            return false
        } else if (testUser.login.isBlank()) {
            textRegError = R.string.log_emp
            return false
        } else if (repeatPassword.isBlank()) {
            textRegError = R.string.not_repeat_passw
            return false
        }else if (!(testUser.login.matches(Regex("[a-zA-Z0-9]+"))) || !(testUser.password.matches(
                Regex("[a-zA-Z0-9]+")
            ))
        ) {
            textRegError = R.string.wrong_char
            return false
        } else if (checkLogins()) {
            textRegError = R.string.login_exist
            return false
        } else if (testUser.password.count() < 8 || testUser.password.count() > 32) {
            textRegError = R.string.short_passw
            return false
        } else if (!((testUser.password.contains("[A-Z]".toRegex()) || testUser.password.contains("[a-z]".toRegex())) && testUser.password.contains(
                "[0-9]".toRegex()
            ))
        ) {
            textRegError = R.string.simpl_passw
            return false
        } else if (!(testUser.password.equals(repeatPassword))) {
            textRegError = R.string.not_same_passw
            return false
        }
        return true
    }

    fun usersComprasion(user: User): Boolean {

        return true
//        return !((testUser.login!=user.login) || (testUser.password!=user.password)|| !(testUser.login.contains("[a-zA-Z0-9]+".toRegex()))|| !(testUser.login.contains("[a-zA-Z0-9]+".toRegex())))
        //  return !(!(testUser.login.matches(Regex("[a-zA-Z0-9]+")))|| !(testUser.password.matches(Regex("[a-zA-Z0-9]+"))))
        // return android.util.Patterns.EMAIL_ADDRESS.matcher(testUser.login).matches()
    }

    fun checkLogins(): Boolean {
        for (i in users) {
            if (testUser.login == i.login)
                return true
        }
        return false
    }

    fun checkPass(): Boolean {
        for (i in users) {
            if (testUser.login == i.login)
                if (testUser.password == i.password)
                    return true
        }
        return false
    }
}
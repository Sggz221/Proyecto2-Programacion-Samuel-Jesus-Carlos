package org.example.newteam.gestion.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.sesion.Session
import org.example.newteam.routes.RoutesManager
import org.mindrot.jbcrypt.BCrypt

class LoginController {
    private val dao = Dependencies.provideUserDao()
    @FXML
    lateinit var acercaDeButton: Button
    @FXML
    lateinit var errorMessage: Label
    @FXML
    lateinit var passwordText: PasswordField
    @FXML
    lateinit var userText: TextField
    @FXML
    lateinit var loginButton: Button

    @FXML
    fun initialize() {
        initEvents()
    }

    private fun initEvents() {
        acercaDeButton.setOnAction {
            RoutesManager.initAboutStage()
        }

        loginButton.setOnAction {
            handleLogin()
        }

        loginButton.setOnKeyTyped { event ->
            if(event.code == KeyCode.ENTER) handleLogin()
        }

        userText.textProperty().addListener { _, oldValue, newValue ->
            if (oldValue != newValue) {
                errorMessage.text = ""
                userText.style = "-fx-border-color: rgba(0,0,0,0);"
                passwordText.style = "-fx-border-color: rgba(0,0,0,0);"
            }
        }
        passwordText.textProperty().addListener { _, oldValue, newValue ->
            if (oldValue != newValue) {
                errorMessage.text = ""
                userText.style = "-fx-border-color: rgba(0,0,0,0);" +
                        "-fx-border-width: 1px;"
                passwordText.style = "-fx-border-color: rgba(0,0,0,0);"
            }
        }
    }

    private fun handleLogin() {
        if (!validateForm(userText.text, passwordText.text)) showUserError()
        if (userText.text == "admin" && BCrypt.checkpw(passwordText.text, dao.getPassword(userText.text))) Session.launchAdmin(userText.text, passwordText.text, userText.scene.window as Stage)
        else if (userText.text == "user" && BCrypt.checkpw(passwordText.text, dao.getPassword(userText.text))) Session.launchUser(userText.text, passwordText.text, userText.scene.window as Stage)
        showUserError()
    }

    private fun showUserError() {
        errorMessage.style = "-fx-text-fill: #FF2C2C;"
        userText.style = "-fx-border-color: #FF2C2C;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 3"
        passwordText.style = "-fx-border-color: #FF2C2C;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 3"
        errorMessage.text = "El usuario o la contraseña son incorrectos"
    }

    private fun validateForm(username: String, password: String): Boolean {
        val regex = """^[a-zA-Z0-9_@]+$""".toRegex()
        if (!username.matches(regex) || !password.matches(regex)) return false
        return true
    }
}



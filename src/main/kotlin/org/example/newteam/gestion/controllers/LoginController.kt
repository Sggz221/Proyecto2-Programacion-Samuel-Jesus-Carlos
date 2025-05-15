package org.example.newteam.gestion.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.routes.RoutesManager

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
            checkUserForm(userText.text, passwordText.text)
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
    private fun checkUserForm(username: String, password: String): Boolean {
        if (username.isBlank() || password.isBlank()) {
            showUserError()
            return false
        }
        return true
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

}



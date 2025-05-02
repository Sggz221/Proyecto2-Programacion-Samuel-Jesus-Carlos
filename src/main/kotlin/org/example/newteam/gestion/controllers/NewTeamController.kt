package org.example.newteam.gestion.controllers

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import org.example.newteam.routes.RoutesManager

class NewTeamController {
    @FXML
    lateinit var aboutButton: MenuItem

    fun initialize() {
        initEvents()
    }

    private fun initEvents() {
        aboutButton.setOnAction {
            RoutesManager.initAboutStage()
        }
    }
}
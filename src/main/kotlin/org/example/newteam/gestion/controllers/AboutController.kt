package org.example.newteam.gestion.controllers

import com.vaadin.open.Open
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.control.Hyperlink
import org.lighthousegames.logging.logging

class AboutController {
    private val logger = logging()

    @FXML
    lateinit var githubLinkCarlos: Hyperlink

    @FXML
    lateinit var githubLinkSamuel: Hyperlink

    @FXML
    lateinit var githubLinkJesus: Hyperlink

    fun initialize() {
        initLinks()
        initEffects()
    }

    private fun initLinks(){
        githubLinkCarlos.setOnAction {
            logger.debug{ "Abriendo Github de Carlos" }
            Open.open("https://github.com/charlieecy")
        }
        githubLinkSamuel.setOnAction {
            logger.debug{ "Abriendo Github de Samuel" }
            Open.open("https://github.com/Sggz221")
        }


        githubLinkJesus.setOnAction {
            logger.debug{ "Abriendo Github de Samuel" }
            Open.open("https://github.com/JesusCoboArrogante")
        }
    }

    private fun initEffects() {
        githubLinkCarlos.setOnMouseEntered {
            githubLinkCarlos.scene.cursor = Cursor.HAND
        }

        githubLinkSamuel.setOnMouseClicked {
            githubLinkSamuel.scene.cursor = Cursor.HAND
        }
        githubLinkJesus.setOnMouseClicked {
            githubLinkJesus.scene.cursor = Cursor.HAND
        }
    }
}
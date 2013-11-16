package nif.tictactoe.controllers;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;

import nif.tictactoe.Context;
import nif.tictactoe.MainEntryPoint;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class ErrorController {

	@FXML
	private void onSubmitClick() {
		try {
			String error = URLEncoder.encode(Context.getContext().getLastError().toString(), "UTF-8");			
			URI msg = new URI("mailto:bizzi_niedda@hotmail.com?subject=Error%20in%20BiddingTicTacToe&body=".concat(error));			
			Desktop.getDesktop().mail(msg);
			MainEntryPoint.closeDialog();
			MainEntryPoint.resetGameAfterError();
		} catch (Exception e) {
			//If it fails to submit a fail -.-
			Platform.exit();
		}
	}
}
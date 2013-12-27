package nif.tictactoe.controllers;

import java.awt.Desktop;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URLEncoder;

import nif.tictactoe.Context;
import nif.tictactoe.FxmlHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;

/*
 * Controller class for unexpected errors.
 * Linked to the ErrorView.fxml
 */
public class ErrorController {

	@FXML
	private void onSubmitClick() {
		try {
			String error = URLEncoder.encode(getStackTrace(Context.getContext().getLastError()), "UTF-8");
			URI msg = new URI("mailto:bizzi_niedda@hotmail.com?subject=Error%20in%20BiddingTicTacToe&body=".concat(error));			
			Desktop.getDesktop().mail(msg);
			FxmlHelper.getInstance().closeDialog();
			FxmlHelper.getInstance().resetGameAfterError();
		} catch (Exception e) {
			//If it fails to submit a fail exit the application.
			Platform.exit();
		}
	}
	
	private String getStackTrace(Throwable throwable) {
	     StringWriter sw = new StringWriter();
	     PrintWriter pw = new PrintWriter(sw, true);
	     throwable.printStackTrace(pw);
	     return sw.getBuffer().toString();
	}
}
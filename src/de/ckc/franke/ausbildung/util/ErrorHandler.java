package de.ckc.franke.ausbildung.util;

import enums.ErrorCode;

public class ErrorHandler {
		String message;

		public ErrorHandler(ErrorCode err) {
			this.message = getMessage(err);
		}

		public String getMessage(ErrorCode err) {
			return message;
			
//			switch (err) {
//			case NOT_A_NUMBER:
//				message = "Not a number";
//			case INVALID_INPUT:
//				message =  "Invalid input";
//			case NO_RESERVATIONS:
//				message = "No reservations found";
//			case INVALID_ID:
//				message = "Invalid ID";
//			case ID_NOT_FOUND:
//				message = "ID not found";
//			case NO_VEHICLES:
//				message = "No vehicles found";
//			case INVALID_DATE:
//				message = "Invalid date";
//			default:
//				message = null;
//			}
//			
//			return message;
		}

		public void setStatus(ErrorCode err) {
			

		}
	}


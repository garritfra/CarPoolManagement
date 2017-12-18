package enums;

public enum ErrorCode {

	NOT_A_NUMBER, INVALID_INPUT, NO_RESERVATIONS, INVALID_ID, ID_NOT_FOUND, NO_VEHICLES, INVALID_DATE;

	public String getMessage(ErrorCode err) {
		String message;
		// TODO Auto-generated method stub
		switch (err) {
		case NOT_A_NUMBER:
			message = "Not a number";
			break;
		case INVALID_INPUT:
			message =  "Invalid input";
			break;
		case NO_RESERVATIONS:
			message = "No reservations found";
			break;
		case INVALID_ID:
			message = "Invalid ID";
			break;
		case ID_NOT_FOUND:
			message = "ID not found";
			break;
		case NO_VEHICLES:
			message = "No vehicles found";
			break;
		case INVALID_DATE:
			message = "Invalid date";
			break;
		default:
			message = null;
			break;
		}
		
		return message;
	}
}

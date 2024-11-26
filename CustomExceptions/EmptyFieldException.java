class EmptyFieldException extends RuntimeException {
  // if the text field is empty
  public EmptyFieldException() {

  }// end constructor

  public EmptyFieldException(String reason) {
    super(reason);
  }// end constructor
}// end EmptyFieldException
class NotALetterException extends RuntimeException {
  // if there are special characters or numbers in the text field
  public NotALetterException() {

  }// end constructor

  public NotALetterException(String reason) {
    super(reason);
  }// end constructor
}// end NotALetterException
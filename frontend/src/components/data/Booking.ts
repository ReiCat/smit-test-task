export default class Booking {
  id: string = "";
  workshopID: string = "";
  contactInformation: string = "";

  getConstructorFor(): any | null {
    return Booking;
  }

  clone(): Booking {
    const newBooking = new Booking();
    newBooking.setValues(this);
    return newBooking;
  }

  setValues(bookingClass: Booking) {
    this.id = bookingClass.id;
    this.workshopID = bookingClass.workshopID;
    this.contactInformation = bookingClass.contactInformation;
  }
}
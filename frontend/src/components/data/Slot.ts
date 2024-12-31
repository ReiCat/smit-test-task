export default class Slot {
  id: string = "";
  time: string = "";
  available: boolean = false;

  getConstructorFor(): any | null {
    return Slot;
  }

  clone(): Slot {
    const newSlot = new Slot();
    newSlot.setValues(this);
    return newSlot;
  }

  setValues(slotClass: Slot) {
    this.id = slotClass.id;
    this.time = slotClass.time;
    this.available = slotClass.available;
  }
}
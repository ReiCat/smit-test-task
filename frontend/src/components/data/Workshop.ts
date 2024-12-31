import { VEHICLE_TYPES } from "../../constants/constants"

export default class Workshop {
  id: string = "";
  name: string = "";
  address: string = "";
  vehicleTypes: VEHICLE_TYPES = VEHICLE_TYPES.CAR;

  getConstructorFor(): any | null {
    return Workshop;
  }

  clone(): Workshop {
    const newWorkshop = new Workshop();
    newWorkshop.setValues(this);
    return newWorkshop;
  }

  setValues(workshopClass: Workshop) {
    this.id = workshopClass.id;
    this.name = workshopClass.name;
    this.address = workshopClass.address;
    this.vehicleTypes = workshopClass.vehicleTypes;
  }
}
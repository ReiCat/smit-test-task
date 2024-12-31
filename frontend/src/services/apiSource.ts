import * as Api from "./api-client";
import Workshop from "../components/data/Workshop";
import Slot from "../components/data/Slot";
import Booking from "../components/data/Booking";
import * as ApiPaths from "../constants/apiPaths";


export async function fetchWorkshops(): Promise<Workshop[]> {
  return await Api.get(ApiPaths.PATH_WORKSHOPS, Workshop);
};

export async function fetchSlots(workshopID: string, from: string, to: string): Promise<Slot[]> {
  const params = new URLSearchParams();

  if (workshopID !== undefined) {
    params.append("workshopID", workshopID);
  }

  if (from !== undefined) {
    params.append("from", from);
  }

  if (to !== undefined) {
    params.append("to", to);
  }

  return await Api.get<Slot[]>(`${ApiPaths.PATH_SLOTS}?${params}`);
};

export async function bookSlot(booking: Booking): Promise<Booking> {
  return await Api.post<Booking>(ApiPaths.PATH_BOOKINGS, booking);
};
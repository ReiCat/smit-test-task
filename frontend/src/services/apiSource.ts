import axios from 'axios';

import Workshop from "../components/data/Workshop";
import Slot from "../components/data/Slot";
import Booking from "../components/data/Booking";
import * as ApiPaths from "../constants/apiPaths";

const APP_JSON_TYPE = "application/json";

export async function fetchWorkshops(): Promise<Workshop[]> {
  const { data } = await axios.get<Workshop[]>(
    ApiPaths.PATH_WORKSHOPS,
    {
      headers: {
        Accept: APP_JSON_TYPE,
      },
    },
  )

  return data;
};

export async function fetchSlots(workshopID: string, from: string, to: string): Promise<Slot[] | any> {
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

  const { data } = await axios.get<Slot[]>(
    `${ApiPaths.PATH_SLOTS}?${params}`,
    {
      headers: {
        Accept: APP_JSON_TYPE,
      },
    },
  )

  return data;
};

export async function bookSlot(booking: Booking): Promise<Booking | any> {
  const { data } = await axios.post<Booking>(
    ApiPaths.PATH_BOOKINGS,
    booking,
    {
      headers: {
        'Content-Type': APP_JSON_TYPE,
        Accept: APP_JSON_TYPE,
      },
    },
  );

  return data;
};
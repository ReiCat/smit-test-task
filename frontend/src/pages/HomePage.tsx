import React, { useState, useEffect } from "react";
import Table from "react-bootstrap/Table";
import Alert from "react-bootstrap/Alert";
import Button from "react-bootstrap/Button";
import { LinkContainer } from "react-router-bootstrap";
import WorkshopForm from "../components/WorkshopForm";
import { fetchWorkshops, fetchSlots } from "../services/apiSource";
import Workshop from "../components/data/Workshop";
import Slot from "../components/data/Slot";

import { LINK_PATHS } from "../constants/paths";
import { VEHICLE_TYPES } from "../constants/constants";

interface HomePageProps {}

const HomePage: React.FC<HomePageProps> = (
  props: HomePageProps
): JSX.Element => {
  const [error, setError] = useState<string>("");
  const [workshops, setWorkshops] = useState<Workshop[]>([]);
  const [slots, setSlots] = useState<Slot[]>([]);
  const [vehicleTypes, setVehicleTypes] = useState<string[]>([]);
  const [selectedVehicleType, setSelectedVehicleType] = useState<string>("");
  const [selectedWorkshopID, setSelectedWorkshopID] = useState<string>("");
  const [dateFrom, setDateFrom] = useState<string>("");
  const [dateTo, setDateTo] = useState<string>("");

  useEffect(() => {
    fetchWorkshops()
      .then((workshopEntries) => {
        setWorkshops(workshopEntries);
      })
      .catch((err) => {
        setError(err.response.data.message);
      });
  }, []);

  useEffect(() => {
    const vehicleTypes: string[] = [];
    for (let vehicle_type of Object.values(VEHICLE_TYPES)) {
      vehicleTypes.push(vehicle_type);
    }

    setVehicleTypes(vehicleTypes);
  }, []);

  useEffect(() => {
    const today = new Date().toISOString().split("T")[0];
    setDateFrom(today);
    setDateTo(today);
  }, []);

  useEffect(() => {
    fetchSlots(selectedWorkshopID, dateFrom, dateTo)
      .then((slotEntries) => {
        setSlots(slotEntries);
      })
      .catch((err) => {
        setError(err.response.data.message);
      });
  }, [selectedVehicleType, selectedWorkshopID, dateFrom, dateTo]);

  return (
    <>
      <WorkshopForm
        workshops={workshops}
        vehicleTypes={vehicleTypes}
        selectedVehicleType={selectedVehicleType}
        setSelectedVehicleType={setSelectedVehicleType}
        selectedWorkshopID={selectedWorkshopID}
        setSelectedWorkshopID={setSelectedWorkshopID}
        dateFrom={dateFrom}
        setDateFrom={setDateFrom}
        dateTo={dateTo}
        setDateTo={setDateTo}
      />
      {error !== "" ? (
        <Alert className="mt-3">
          <b>{error}</b>
        </Alert>
      ) : null}
      {Array.isArray(slots) && slots.length > 0 ? (
        <Table className="mt-3" striped bordered hover>
          <thead>
            <tr>
              <th>Time</th>
              <th>Available</th>
            </tr>
          </thead>
          <tbody>
            {slots.map((slot) => {
              return (
                <tr key={slot.id}>
                  <td>{slot.time}</td>
                  <td>{slot.available}</td>
                </tr>
              );
            })}
          </tbody>
        </Table>
      ) : (
        <Alert className="mt-3">
          <b>No slots found</b>
        </Alert>
      )}
    </>
  );
};

export default HomePage;

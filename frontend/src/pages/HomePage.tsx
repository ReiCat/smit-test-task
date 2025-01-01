import React, { useState, useEffect } from "react";
import Table from "react-bootstrap/Table";
import Alert from "react-bootstrap/Alert";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import WorkshopForm from "../components/WorkshopForm";
import BookSlotForm from "../components/BookSlotForm";
import { fetchWorkshops, fetchSlots } from "../services/apiSource";
import Workshop from "../components/data/Workshop";
import Slot from "../components/data/Slot";
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
  const [selectedWorkshop, setSelectedWorkshop] = useState<Workshop>();
  const [selectedSlotTimeAvailable, setSelectedSlotTimeAvailable] =
    useState<string>("");
  const [selectedSlotID, setSelectedSlotID] = useState<string>("");
  const [dateFrom, setDateFrom] = useState<string>("");
  const [dateTo, setDateTo] = useState<string>("");
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [success, setSuccess] = useState<boolean>(false);

  useEffect(() => {
    fetchWorkshops()
      .then((workshopEntries) => {
        setWorkshops(workshopEntries);
        setError("");
      })
      .catch((err) => {
        setError(err.response.statusText);
        setWorkshops([]);
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
        setError("");
      })
      .catch((err) => {
        setError(err.response.statusText);
        setSlots([]);
      });
  }, [selectedVehicleType, selectedWorkshopID, dateFrom, dateTo]);

  const openModalWithData = (
    selectedWorkshopID: string,
    slotID: string,
    timeAvailable: string
  ) => {
    setSelectedWorkshop(
      workshops.find(
        (workshop) => parseInt(workshop.id) === parseInt(selectedWorkshopID)
      )
    );
    setSelectedSlotID(slotID);
    setSelectedSlotTimeAvailable(timeAvailable);
    handleShow();
  };

  return (
    <>
      <WorkshopForm
        setError={setError}
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
      {error !== "" && error !== undefined ? (
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
                  <td>
                    <div className="d-flex justify-content-center mt-3">
                      <Button
                        variant="primary"
                        onClick={() =>
                          openModalWithData(
                            selectedWorkshopID,
                            slot.id,
                            slot.time
                          )
                        }
                      >
                        Book slot
                      </Button>
                    </div>
                  </td>
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
      <Modal show={show} onHide={handleClose} centered>
        <Modal.Header closeButton>
          <Modal.Title>Book slot</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {success === true ? (
            <Alert variant="success">
              <Alert.Heading>Success!</Alert.Heading>
            </Alert>
          ) : (
            <BookSlotForm
              workshop={selectedWorkshop!}
              slotID={selectedSlotID}
              timeAvailable={selectedSlotTimeAvailable}
              setSuccess={setSuccess}
            />
          )}
        </Modal.Body>
      </Modal>
    </>
  );
};

export default HomePage;

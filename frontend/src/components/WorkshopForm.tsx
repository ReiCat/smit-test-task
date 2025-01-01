import React, {
  FunctionComponent,
  useState,
  useEffect,
  ChangeEvent,
} from "react";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";

import { useFormik } from "formik";
import * as Yup from "yup";

import Workshop from "../components/data/Workshop";
import { contains } from "../utils/utils";

export type Option = {
  [value: string]: any;
};

interface WorkshopFormProps {
  setError: Function;
  workshops: Workshop[];
  vehicleTypes: string[];
  selectedVehicleType: string;
  setSelectedVehicleType: Function;
  selectedWorkshopID: string;
  setSelectedWorkshopID: Function;
  dateFrom: string;
  setDateFrom: Function;
  dateTo: string;
  setDateTo: Function;
}

const WorkshopForm: FunctionComponent<WorkshopFormProps> = (
  props: WorkshopFormProps
): JSX.Element => {
  const [availableWorkshops, setAvailableWorkshops] = useState<Workshop[]>([]);

  const bookingForm = useFormik({
    initialValues: {
      selectedVehicleType: props.selectedVehicleType,
      workshopID: props.selectedWorkshopID,
      dateFrom: props.dateFrom,
      dateTo: props.dateTo,
    },
    validationSchema: Yup.object({
      dateFrom: Yup.date().min(
        new Date(new Date().getTime() - 86400000),
        "Date to must be later than today."
      ),
      dateTo: Yup.date().min(
        new Date(new Date().getTime() - 86400000),
        "Date to must be later than today."
      ),
    }),
    onSubmit: async (values) => {},
  });

  useEffect(() => {
    if (props.vehicleTypes.length > 0) {
      props.setSelectedVehicleType(props.vehicleTypes[0]);
    }
  }, [props.vehicleTypes]);

  useEffect(() => {
    const filteredWorkshops: Workshop[] = [];
    for (let i = 0; i < props.workshops.length; i++) {
      if (
        contains(props.workshops[i].vehicleTypes, props.selectedVehicleType)
      ) {
        filteredWorkshops.push(props.workshops[i]);
      }
    }

    if (filteredWorkshops.length > 0) setAvailableWorkshops(filteredWorkshops);
  }, [props.workshops, props.selectedVehicleType]);

  useEffect(() => {
    if (availableWorkshops.length > 0) {
      props.setSelectedWorkshopID(availableWorkshops[0].id);
    }
  }, [availableWorkshops]);

  useEffect(() => {
    const dateFrom = new Date(props.dateFrom);
    const dateTo = new Date(props.dateTo);
    if (dateTo < dateFrom) {
      props.setError("Date to could not be earlier than date from.");
    } else {
      props.setError("");
    }
  }, [props.dateFrom, props.dateTo]);

  const handleVehicleTypeSelectChange = (e: ChangeEvent<HTMLSelectElement>) => {
    props.setSelectedVehicleType(e.target.value);
  };

  const handleWorkshopSelectChange = (e: ChangeEvent<HTMLSelectElement>) => {
    props.setSelectedWorkshopID(
      e.target.options[e.target.options.selectedIndex].id
    );
  };

  const handleDateFromChange = (e: ChangeEvent<HTMLInputElement>) => {
    props.setDateFrom(e.target.value);
    bookingForm.handleChange(e);
  };

  const handleDateToChange = (e: ChangeEvent<HTMLInputElement>) => {
    props.setDateTo(e.target.value);
    bookingForm.handleChange(e);
  };

  return (
    <Form>
      <Row className="mb-3">
        <Form.Group as={Col} md="6" controlId="vehicle">
          <Form.Label>Vehicle type</Form.Label>
          <Form.Select required onChange={handleVehicleTypeSelectChange}>
            {props.vehicleTypes.map((vehicleType) => {
              return (
                <option
                  value={vehicleType}
                  selected={vehicleType == props.selectedVehicleType}
                >
                  {vehicleType}
                </option>
              );
            })}
          </Form.Select>
        </Form.Group>
        <Form.Group as={Col} md="6" controlId="workshop">
          <Form.Label>Workshop</Form.Label>
          <Form.Select required onChange={handleWorkshopSelectChange}>
            {availableWorkshops.map((workshop) => {
              return (
                <option
                  id={workshop.id}
                  selected={bookingForm.values.workshopID == workshop.id}
                >
                  {workshop.name}
                </option>
              );
            })}
          </Form.Select>
        </Form.Group>
      </Row>
      <Row className="mb-3">
        <Form.Group as={Col} md="6" controlId="date_from">
          <Form.Label>Date from</Form.Label>
          <Form.Control
            type="date"
            name="dateFrom"
            placeholder="Date from"
            onChange={handleDateFromChange}
            value={props.dateFrom}
            isInvalid={!!bookingForm.errors?.dateFrom}
          />
        </Form.Group>
        <Form.Group as={Col} md="6" controlId="date_to">
          <Form.Label>Date to</Form.Label>
          <Form.Control
            type="date"
            name="dateTo"
            placeholder="Date to"
            onChange={handleDateToChange}
            value={props.dateTo}
            isInvalid={!!bookingForm.errors?.dateTo}
          />
        </Form.Group>
      </Row>
    </Form>
  );
};

export default WorkshopForm;

import React, {
  FunctionComponent,
  useState,
  ChangeEvent,
  useEffect,
} from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Alert from "react-bootstrap/Alert";
import axios from "axios";

import { useFormik } from "formik";
import * as Yup from "yup";

import Workshop from "./data/Workshop";
import Booking from "./data/Booking";
import { bookSlot } from "../services/apiSource";

export type Option = {
  [value: string]: any;
};

interface BookSlotFormProps {
  workshop: Workshop;
  slotID: string;
  timeAvailable: string;
  setSuccessTime: Function;
  handleClose: Function;
}

const BookSlotForm: FunctionComponent<BookSlotFormProps> = (
  props: BookSlotFormProps
): JSX.Element => {
  const [error, setError] = useState<string>("");

  const bookSlotForm = useFormik({
    initialValues: {
      id: props.slotID,
      workshopID: props.workshop.id,
      contactInformation: "",
    },
    validationSchema: Yup.object({
      id: Yup.string().required(),
      workshopID: Yup.string().required(),
      contactInformation: Yup.string().required(),
    }),
    onSubmit: async (values) => {
      const booking: Booking = new Booking();
      booking.id = values.id;
      booking.workshopID = values.workshopID;
      booking.contactInformation = values.contactInformation;
      bookSlot(booking)
        .then((res: any) => {
          props.setSuccessTime(res["time"]);
        })
        .catch((err) => {
          if (axios.isAxiosError(err)) {
            if (err.response !== undefined) {
              setError(err.response.statusText);
            } else {
              setError(err.message);
            }
          } else {
            setError("An unexpected error occurred");
          }
        });
      props.handleClose();
    },
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    bookSlotForm.handleChange(e);
  };

  return (
    <Form onSubmit={bookSlotForm.handleSubmit}>
      <Row className="mb-3">
        <Form.Group as={Col} md="12" controlId="contactInformation">
          <Form.Control
            required
            type="textarea"
            placeholder="Contact information"
            onChange={handleChange}
            value={bookSlotForm.values.contactInformation}
            isInvalid={!!bookSlotForm.errors?.contactInformation}
          />
        </Form.Group>
      </Row>
      <Row className="mb-3">
        <Form.Group as={Col} md="12" controlId="workshop">
          <Button type="submit" variant="secondary">
            Submit
          </Button>
        </Form.Group>
      </Row>
      {error !== "" ? (
        <Alert className="mt-3">
          <b>{error}</b>
        </Alert>
      ) : null}
    </Form>
  );
};

export default BookSlotForm;

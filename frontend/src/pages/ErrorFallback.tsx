import { FunctionComponent } from "react";
import Alert from "react-bootstrap/Alert";

interface ErrorFallbackProps {
  error: any;
}

const ErrorFallback: FunctionComponent<ErrorFallbackProps> = ({ error }) => {
  if (error.isAxiosError) {
    switch (error.response.status) {
      case 403:
        return (
          <Alert dismissible variant="danger">
            <Alert.Heading>Oh snap! You got an error!</Alert.Heading>
            <p>Change this and that and try again.</p>
          </Alert>
        );
      case 503:
        return (
          <Alert dismissible variant="danger">
            <Alert.Heading>Oh snap! You got an error!</Alert.Heading>
            <p>Change this and that and try again.</p>
          </Alert>
        );
    }
  }

  return <>Error has appeared</>;
};

export default ErrorFallback;

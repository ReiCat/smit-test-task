import { Route } from "react-router";

import { ErrorBoundary } from "react-error-boundary";
import ErrorFallback from "../pages/ErrorFallback";
import { RouteConfig } from "../constants/router";

const Router = {
  buildRoutes(routes: RouteConfig[]) {
    return routes.map(({ path, children, ...rest }, key) => {
      return (
        <Route
          key={key}
          path={path}
          {...rest}
          element={
            <ErrorBoundary FallbackComponent={ErrorFallback}>
              {children}
            </ErrorBoundary>
          }
        />
      );
    });
  },
};

export default Router;

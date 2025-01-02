import { LINK_PATHS } from "./paths";
import HomePage from "../pages/HomePage";
import PageNotFound from "../pages/PageNotFound";

export interface RouteConfig {
  path: string;
  children?: React.ReactNode;
  [key: string]: any;
}

export const APP_ROUTERS: RouteConfig[] = [
  {
    path: LINK_PATHS.homePage,
    children: <HomePage />,
  },
  {
    path: LINK_PATHS.notFound,
    children: <PageNotFound />,
  },
];

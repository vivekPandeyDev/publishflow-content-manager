import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import "react-toastify/dist/ReactToastify.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import About from "./screen/About";

import Home from "./screen/Home";
import SignUp from "./screen/SignUp";

const router = createBrowserRouter([
	{
		path: "/",
		element: <Home />,
		children: [
			{
				path: "about/:id",
				element: <About />,
			},
			{
				path : "sign-up",
				element : <SignUp/>
			},
			{ index: true, element: <div>Home page</div> },
			
		],
	}

]);

ReactDOM.createRoot(document.getElementById("root")!).render(
	<React.StrictMode>
		<RouterProvider router={router} />
	</React.StrictMode>
);

import { Outlet, useNavigation } from "react-router-dom";
import Navbar from "../components/Navbar";

export default function Home() {
  const { state } = useNavigation();
  if (state === "loading") {
    return <div>Loading ...</div>;
  }


  return (
    <div className="flex h-screen flex-col">
      <Navbar title="publish-flow"/>
      <Outlet/>
    </div>
  );
}

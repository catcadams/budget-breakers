import { useState } from "react";
import "./App.css";
import { Routes, Route, Link } from "react-router-dom";
import RegisterForm from "./components/RegisterForm";
import LoginForm from "./components/LoginForm";
import Events from "./components/Pages/Events";
import Chores from "./components/Pages/Chores";
import Navbar from "./components/Navbar";
import ChoreCreationForm from "./components/ChoreCreationForm";
import ChoresList from "./components/ChoresList";
import SingleChorePage from "./components/SingleChorePage";
import Home from "./components/Pages/Home";
import Logout from "./components/Logout";
import EditChorePage from "./components/EditChorePage";
import CreateGroupForm from "./components/CreateGroupForm";
import EventDetails from "./components/EventDetails";
import ViewEvents from "./components/ViewEvents";
import UpdateEventDetails from "./components/UpdateEventDetails";
import GroupsList from "./components/GroupsList";
import Groups from "./components/Pages/Groups";
import SingleGroupPage from "./components/SingleGroupPage";
import EditGroupForm from "./components/EditGroupForm";
import CreateEvent from "./components/CreateEvent";
import AcceptInvitation from "./components/AcceptInvitation";
import ChoreCompletionPage from "./components/ChoreCompletionPage";
import AddNewMember from "./components/AddNewMember";
import LoginNavbar from "./components/LoginNavBar";
import { Outlet } from "react-router-dom";
import UserAccount from "./components/UserAccount";

function App() {
  const AppLayout = () => (
    <>
      <Navbar />
      <Outlet />
    </>
  );

  const LoginLayout = () => (
    <>
      <LoginNavbar />
      <Outlet />
    </>
  );

  return (
    <>
      <Routes>
        <Route element={<LoginLayout />}>
          <Route path="/register" element={<RegisterForm />} />
          <Route path="/login" element={<LoginForm />} />
        </Route>
        <Route element={<AppLayout />}>
          <Route path="/Home" element={<Home />} />
          <Route path="/Events" element={<Events />} />
          <Route path="/event/create" element={<CreateEvent />} />
          <Route
            path="/events/:userGroupId/:eventId"
            element={<EventDetails />}
          />
          <Route path="/events/:groupID/list" element={<ViewEvents />} />
          <Route
            path="/events/edit/:userGroupId/:eventId"
            element={<UpdateEventDetails />}
          />
          <Route path="/Chores" element={<Chores />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="/Groups" element={<Groups />} />
          <Route path="/chores/create" element={<ChoreCreationForm />} />
          <Route path="/chores/1/list" element={<ChoresList />} />
          <Route path="/chores/:choreId" element={<SingleChorePage />} />
          <Route path="/chores/:userGroupId/list" element={<ChoresList />} />
          <Route
            path="/chores/:userGroupId/:choreId"
            element={<SingleChorePage />}
          />
          <Route path="/chores/:choreId/edit" element={<EditChorePage />} />
          <Route path="/groups/create" element={<CreateGroupForm />} />
          <Route path="/invite/accept" element={<AcceptInvitation />} />
          <Route path="/groups/:userID/list" element={<GroupsList />} />
          <Route
            path="/groups/:userID/:groupID"
            element={<SingleGroupPage />}
          />
          <Route
            path="/groups/:userID/:groupID/edit"
            element={<EditGroupForm />}
          />
          <Route
            path="/groups/:userID/:groupID/add-member"
            element={<AddNewMember />}
          />
          <Route
            path="/chores/:choreId/congrats"
            element={<ChoreCompletionPage />}
          />
          <Route path="/user" element={<UserAccount />} />
        </Route>
      </Routes>
    </>
  );
}

export default App;

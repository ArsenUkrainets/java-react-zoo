import React from "react";
import { Routes, Route } from "react-router-dom";
//import logo from './logo.svg';
import "./App.css";
import HomePage from "./components/Home";
import DefaultLayout from "./components/containers/DefaultLayout";

function App() { // create component App to rendering element
  return ( 
    <>
      <Routes>
        <Route path="/" element={<DefaultLayout />}> {/**set function with element, wich import 'DefaultLayout' to which use antd */}
          <Route index element={<HomePage />} />  {/**set function, import 'HomePage' to set the image */}
        </Route>
      </Routes>
    </>
  );
}

export default App;

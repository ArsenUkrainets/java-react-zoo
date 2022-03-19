import * as React from "react";
import "cropperjs/dist/cropper.min.css";
import { Modal, Col, Row } from "antd";
import Cropper from "cropperjs";
import { ICropperProps } from "./types";

const CropperModal: React.FC<ICropperProps> = ({ //hook
  onSelected,
  aspectRation=1/1 //parameter
}) => {
  const [visible, setVisible] = React.useState(false); // new state variable 'visible' with function 'setVisible'

  const imgRef = React.useRef<HTMLImageElement>(null); // useRef will return a  variable object to ref, .current property is initialized by the passed argument
  const prevRef = React.useRef<HTMLImageElement>(null); 
  const [cropperObj, setCropperObj] = React.useState<Cropper>(); // new state variable 'cropperObj' with function 'setCropperObj'

  //choose file
  const handleChangeFile = async (e: any) => { //async element
    const file = (e.target.files as FileList)[0];
    if (file) {
      const url = URL.createObjectURL(file);

      await setVisible(true);
      console.log("Image ref ", imgRef);
      let cropper = cropperObj;
      if (!cropper) { // if cropper undefined
        cropper = new Cropper(imgRef.current as HTMLImageElement, { //creating new cropper with using imgRef
          aspectRatio: aspectRation, //set parameter
          viewMode: 1, // property 'viewMode'
          preview: prevRef.current as HTMLImageElement, // set property 'preview' : ref(null) as interface 'HTMLImageElement'
        });
      }
      cropper.replace(url); //take new url for cropper
      e.target.value = "";
      setCropperObj(cropper);
    }
  };
  const handleOk = async () => { // use async component
    const base64 = cropperObj?.getCroppedCanvas().toDataURL() as string; // convert cropperObj url to string to set in base64
    await setVisible(false);
    onSelected(base64);
  };
  return (
    <>
      <label htmlFor="uploading"> 
        <img src="https://www.iconpacks.net/icons/2/free-click-icon-2384-thumb.png" alt="" width="100%" style={{ cursor: "pointer" }} />
      </label>

      <input
        id="uploading"
        style={{ display: "none" }}
        type="file"
        onChange={handleChangeFile}
      />

      <Modal
        title="Modal 1000px width"
        centered
        visible={visible}
        onOk={handleOk}
        onCancel={() => setVisible(false)}
        width={1000}
      >
        <Row gutter={[8, 8]}>
          <Col md={18} xs={24}>
            <div>
              <img
                src="https://vovalohika.tk/images/1200_gntox1zw.ipw.jpeg"
                width="100%"
                style={{ maxHeight: "600px" }}
                ref={imgRef}
              />
            </div>
          </Col>
          <Col md={6} xs={24}>
            <div
              ref={prevRef}
              style={{
                height: "150px",
                border: "1px solid silver",
                overflow: "hidden",
              }}
            ></div>
          </Col>
        </Row>
      </Modal>
    </>
  );
};

export default CropperModal;
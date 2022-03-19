import * as React from 'react';
import CropperModal from '../common/CropperModal';
import { Col, Row } from "antd";
import http, {urlBackend} from '../../http_common'; //import hook  

const HomePage : React.FC = () => { 

  const [images, setImages] = React.useState<Array<string>>([]); // new state variable 'images' with function 'setImages'

  const handleSelected = async (base64: string) => { // use async component to readability render functions
    console.log("Select "+ base64);
    const imgName = await http.post<string>("upload", {base64: base64}); //await upload image
    console.log(imgName.data);
    
    setImages([...images,urlBackend+"files/"+imgName.data]); // setting image
  };

  const dataImages = images.map((item, key) => { // arrow function component
    return (
      <Col md={4} key={key}>
        <div>
          <img src={item} alt="images" width="100%" />
        </div>
      </Col>
    );
  });


    return (
      <>
        <h1>Main page</h1>
        
        <Row gutter={[8, 8]}>
        {dataImages}
        <Col md={4}>
          <div>
            <CropperModal onSelected={handleSelected} /> {/**return component with attribute, which set function 'handleSelected' */}
          </div> 
        </Col>
      </Row>
        

      </>
    );
}

export default HomePage;
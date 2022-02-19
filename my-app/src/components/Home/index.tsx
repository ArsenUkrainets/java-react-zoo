import * as React from 'react';
import CropperModal from '../common/CropperModal';
import { Col, Row } from "antd";
import http, {urlBackend} from '../../http_common';

const HomePage : React.FC = () => {

  const [images, setImages] = React.useState<Array<string>>([]);

  const handleSelected = async (base64: string) => {
    console.log("Select "+ base64);
    const imgName = await http.post<string>("upload", {base64: base64});
    console.log(imgName.data);
    
    setImages([...images,urlBackend+"files/"+imgName.data]);
  };

  const dataImages = images.map((item, key) => {
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
            <CropperModal onSelected={handleSelected} />
          </div>
        </Col>
      </Row>
        

      </>
    );
}

export default HomePage;
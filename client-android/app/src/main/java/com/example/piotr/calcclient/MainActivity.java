package com.example.piotr.calcclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {




    private String OPERATION_NAME = "add";
    private String SOAP_ACTION = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl/ICalc/"+OPERATION_NAME;

    private static final String WSDL_TARGET_NAMESPACE = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl";

    private String SOAP_ADDRESS = "http://192.168.1.11:62901/calcService.svc";

    private SoapSerializationEnvelope envelope ;

    private EditText editCalcResult,editCalcException,editCalcRequest,editCalcResponse ;

    private class calcClassResult {
        public String calcResult;
        public String calcException;
        public String calcRequest;
        public String calcResponse;

        public calcClassResult(String result, String _exception, String request, String response ) {
            calcResult=result;
            calcException=_exception;
            calcRequest=request;
            calcResponse=response;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.groupCalcAdd:
                if (checked)
                    OPERATION_NAME = "add";
                    SOAP_ACTION = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl/ICalc/"+OPERATION_NAME;
                    break;
            case R.id.groupCalcDiv:
                if (checked)
                    OPERATION_NAME = "div";
                    SOAP_ACTION = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl/ICalc/"+OPERATION_NAME;
                    break;
            case R.id.groupCalcMul:
                if (checked)
                    OPERATION_NAME = "mul";
                    SOAP_ACTION = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl/ICalc/"+OPERATION_NAME;
                    break;
            case R.id.groupCalcPow:
                if (checked)
                    OPERATION_NAME = "pow";
                    SOAP_ACTION = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl/ICalc/"+OPERATION_NAME;
                    break;
            case R.id.groupCalcSub:
                if (checked)
                    OPERATION_NAME = "sub";
                    SOAP_ACTION = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl/ICalc/"+OPERATION_NAME;
                    break;
        }
    }

    public class MarshalDouble implements Marshal {
        public Object readInstance(XmlPullParser parser, String namespace, String name,
                                   PropertyInfo expected) throws IOException, XmlPullParserException {

            return Double.parseDouble(parser.nextText());
        }


        public void register(SoapSerializationEnvelope cm) {
            cm.addMapping(cm.xsd, "double", Double.class, this);

        }


        public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
            writer.text(obj.toString());
        }
    }

    public void Invoke(View view) {

        EditText editCalcURI = (EditText) findViewById(R.id.editCalcURI);
        SOAP_ADDRESS = editCalcURI.getText().toString();

        editCalcResult = (EditText) findViewById(R.id.editCalcResult);
        editCalcResult.setText("Invoke called");
        editCalcException = (EditText) findViewById(R.id.editCalcException);
        editCalcException.setText("");

        editCalcRequest = (EditText) findViewById(R.id.editCalcRequest);
        editCalcRequest.setText("");

        editCalcResponse = (EditText) findViewById(R.id.editCalcResponse);
        editCalcResponse.setText("");


        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = false;
        envelope.xsi="http://www.w3.org/2001/XMLSchema-instance";
        envelope.encodingStyle = SoapSerializationEnvelope.XSD;



        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

        EditText editCalcValueA = (EditText) findViewById(R.id.editCalcValueA);
        PropertyInfo piA =new PropertyInfo();
        piA.setName("a");
        piA.setValue(Double.parseDouble(editCalcValueA.getText().toString()));
        piA.setType(Double.class);
        request.addProperty(piA);

        EditText editCalcValueB = (EditText) findViewById(R.id.editCalcValueB);
        PropertyInfo piB =new PropertyInfo();
        piB.setName("b");
        piB.setValue(Double.parseDouble(editCalcValueB.getText().toString()));
        piB.setType(Double.class);
        request.addProperty(piB);

        envelope.setOutputSoapObject(request);
        MarshalDouble md = new MarshalDouble();
        md.register(envelope);

 /*       EditText editCalcCall = (EditText) findViewById(R.id.editCalcCall);
        editCalcCall.setText(envelope.bodyOut.toString());
*/
        new CallWeb().execute();
    }

    private class CallWeb extends AsyncTask<Void , Void, calcClassResult> {

        protected void onPreExecute() {
            editCalcResult.setText("Calling SOAP server...");
        }

        @Override
        protected calcClassResult doInBackground(Void... voids) {
            String sLine="",sRequestDump="",sResponseDump="";
            try  {
                sLine="0";
                HttpTransportSE httpTransport = new  HttpTransportSE(SOAP_ADDRESS);
                sLine="10";
                httpTransport.debug = true;
                sLine="20";
                httpTransport.call(SOAP_ACTION, envelope);
                sRequestDump=httpTransport.requestDump.toString();
                sLine="30";
                Object response = envelope.getResponse();
                sResponseDump=httpTransport.responseDump.toString();
                sLine="40";
                return new calcClassResult(response.toString(),"",sRequestDump,sResponseDump);
            }  catch (Exception exception)   {
                return new calcClassResult("",exception.toString(),sRequestDump,sResponseDump);
            }
        }

        protected void onPostExecute(calcClassResult result) {
            editCalcResult.setText(result.calcResult);
            editCalcException.setText(result.calcException);
            editCalcRequest.setText(result.calcRequest);
            editCalcResponse.setText(result.calcResponse);
        }
    }
}

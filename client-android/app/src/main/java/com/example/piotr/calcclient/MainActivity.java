package com.example.piotr.calcclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {




    private String OPERATION_NAME = "add";
    private String SOAP_ACTION = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl/ICalc/"+OPERATION_NAME;

    private static final String WSDL_TARGET_NAMESPACE = "http://websrv.cs.fsu.edu/~engelen/calc.wsdl";

    private String SOAP_ADDRESS = "http://192.168.1.11:62901/calcService.svc";

    private SoapSerializationEnvelope envelope ;

    private TextView textCalcResult ;

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

    public void Invoke(View view) {

        EditText editCalcURI = (EditText) findViewById(R.id.editCalcURI);
        SOAP_ADDRESS = editCalcURI.getText().toString();

        textCalcResult = (TextView) findViewById(R.id.textCalcResult);
        textCalcResult.setText("Invoke called");

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        EditText editCalcValueA = (EditText) findViewById(R.id.editCalcValueA);
        request.addProperty("a", editCalcValueA.getText().toString());
        EditText editCalcValueB = (EditText) findViewById(R.id.editCalcValueB);
        request.addProperty("b", editCalcValueB.getText().toString());

        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        new CallWeb().execute();
    }

    private class CallWeb extends AsyncTask<Void , Void, String> {

        protected void onPreExecute() {
            textCalcResult.setText("Calling SOAP server...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try  {
                HttpTransportSE httpTransport = new  HttpTransportSE(SOAP_ADDRESS);
                httpTransport.call(SOAP_ACTION, envelope);
                Object response = envelope.getResponse();
                return response.toString();

            }  catch (Exception exception)   {
                return exception.toString();
            }
        }

        protected void onPostExecute(String result) {
            textCalcResult.setText(result);
        }
    }
}

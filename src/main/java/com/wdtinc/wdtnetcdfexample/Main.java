package com.wdtinc.wdtnetcdfexample;

import java.util.List;
import ucar.ma2.ArrayFloat;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class Main {

    public static void main(String args[]) throws Exception {

        String fileName = "SkyWise-Global7km-GFSAviation_20170120_1200.nc";
        NetcdfFile dataFile = NetcdfFile.open(fileName, null);

        // print out the file structure
        System.out.println(dataFile.toString());

        // read the lat/lon and temperature variables
        Variable latVar = dataFile.findVariable("latitude");
        Variable lonVar = dataFile.findVariable("longitude");
        Variable turbulenceVar = dataFile.findVariable("turbulence");
        Variable edrVar = dataFile.findVariable("EDR");

        // Read the units for the temperature variable
        System.out.println("units: " + turbulenceVar.findAttributeIgnoreCase("units"));
        System.out.println("units: " + edrVar.findAttributeIgnoreCase("units"));

        // Read the valid time for this data
        Variable time = dataFile.findVariable("time");
        System.out.println("valid time: " + time.findAttributeIgnoreCase("string").getStringValue());

        // if it's a forecast, get the forecast reference time
        Variable forecastTime = dataFile.findVariable("forecast_reference_time");
        System.out.println("forecast time: " + forecastTime.findAttributeIgnoreCase("string").getStringValue());

        // Read the latitude and longitude coordinate variables into arrays
        ArrayFloat.D2 lats = (ArrayFloat.D2) latVar.read();
        ArrayFloat.D2 lons = (ArrayFloat.D2) lonVar.read();

        // Read the turbulence & edr data
        ArrayFloat.D2 turbulence = (ArrayFloat.D2) turbulenceVar.read();
        ArrayFloat.D2 edr = (ArrayFloat.D2) edrVar.read();

        // dimensions of the lat/lon array
        List<Dimension> dimensions = dataFile.getDimensions();
        int lonLength = dimensions.get(0).getLength();
        int latLength = dimensions.get(1).getLength();

        System.out.println("lon length: " + lonLength);
        System.out.println("lat length: " + latLength);

        // iterate through the arrays, do something with the data
        for (int lat = 0; lat < latLength; lat++) {
            for (int lon = 0; lon < lonLength; lon++) {
                // do something with the data
                System.out.print(lats.get(lat, lon) + ",");
                System.out.print(lons.get(lat, lon) + ",");
                System.out.print(turbulence.get(lat, lon) + ",");
                System.out.println(edr.get(lat, lon));
            }
        }
    }
}

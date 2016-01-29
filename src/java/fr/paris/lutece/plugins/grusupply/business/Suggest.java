package fr.paris.lutece.plugins.grusupply.business;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Suggest {

	private String[] input;
	private String output;
	private ArrayList<String> payload;
	
	
	@JsonProperty("input")
	public String[] getInput() {
		return input;
	}
	public void setInput(String[] input) 
	{
		this.input = input;
	}
	@JsonProperty("output")
	public String getOutput() 
	{
		return output;
	}
	public void setOutput(String output)
	{
		this.output = output;
	}

    @JsonProperty("payload")
    public String getPayload() 
	{
    	String strretour = "{";
    	int ntaille = payload.size();
    	for(int i=0;i<ntaille;++i)
    	{
    		strretour += payload.get(i);
    		if(i < ntaille-1) strretour += ",";
    	}
		return strretour+"}";
	}
    public void setPayload(ArrayList<String> payload) 
	{
		this.payload = payload;
	}
}

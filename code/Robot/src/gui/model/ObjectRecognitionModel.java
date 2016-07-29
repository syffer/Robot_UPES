package gui.model;

import features.ChainCode;
import features.PositionnedObject;
import features.Tag;
import geometry.Position;
import image.Image;
import image.MonoImage;
import image.RGBImage;

import java.awt.Color;
import java.util.Collection;
import java.util.Map;

public class ObjectRecognitionModel extends ImageModel {
	
	protected MonoImage previous;
	private Map<Tag, Collection<PositionnedObject>> mapObjects; 
	
	public ObjectRecognitionModel(MonoImage image, Image original, Map<Tag, Collection<PositionnedObject>> mapObjects, double executionTime) { 
		super(new RGBImage(original), image, original, "Object Recognition", executionTime);
		
		this.previous = image;
		this.mapObjects = mapObjects;
		
		for(Map.Entry<Tag, Collection<PositionnedObject>> entry : this.mapObjects.entrySet()) {
			
			Tag tag = entry.getKey();
			Color color = tag.getColor();
			
			System.out.print("-> " + tag + " " + entry.getValue().size() + " : ");
			
			for(PositionnedObject someObject : entry.getValue()) {
				
				System.out.println(someObject.getCompactness() + " ");
				
				Position origin = someObject.getPosition();
				ChainCode chainCode = someObject.getChainCode();
				
				for(Position position : chainCode.getPositions(origin)) {
					this.image.setRGB(position.getI(), position.getJ(), color.getRGB());
				}
			}
			
		}
		
	}
	
}

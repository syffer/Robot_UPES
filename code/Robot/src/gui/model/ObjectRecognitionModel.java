package gui.model;

import features.ChainCode;
import features.PositionnedObject;
import features.Tag;
import geometry.Position;
import image.MonoImage;
import image.RGBImage;

import java.awt.Color;
import java.util.Collection;
import java.util.Map;

public class ObjectRecognitionModel extends ImageModel {
	
	protected MonoImage original;
	private Map<Tag, Collection<PositionnedObject>> mapObjects; 
	
	public ObjectRecognitionModel(MonoImage image, Map<Tag, Collection<PositionnedObject>> mapObjects, double executionTime) { 
		super(new RGBImage(image.getWidth(), image.getHeight()), image, "Object Recognition", executionTime);
		
		this.original = image;
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
					this.image.set(position.getI(), position.getJ(), color.getRGB());
				}
			}
			
		}
		
	}
	
}

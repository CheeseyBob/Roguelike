package roguelike.scene;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


class DrawLayers {
	private Map<Integer, DrawLayer> layers = new HashMap<Integer, DrawLayer>();
	
	void add(Entity entity) {
		DrawLayer drawLayer = layers.get(entity.drawLayer);
		if(drawLayer == null) {
			drawLayer = new DrawLayer();
			layers.put(entity.drawLayer, drawLayer);
		}
		drawLayer.add(entity);
	}

	List<DrawLayer> getLayersInOrder() {
		return layers.keySet()
				.stream()
				.sorted()
				.map(i -> layers.get(i))
				.collect(Collectors.toList());
	}
	
	void remove(Entity entity) {
		DrawLayer drawLayer = layers.get(entity.drawLayer);
		drawLayer.remove(entity);
		if(drawLayer.isEmpty())
			layers.remove(entity.drawLayer);
	}
}

class DrawLayer {
	private Set<Entity> entities = new HashSet<Entity>();
	
	void add(Entity entity) {
		entities.add(entity);
	}
	
	Set<Entity> getEntities() {
		return entities;
	}
	
	boolean isEmpty() {
		return entities.isEmpty();
	}
	
	void remove(Entity entity) {
		entities.remove(entity);
	}
}

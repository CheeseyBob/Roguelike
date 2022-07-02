package roguelike.scene;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


class EntityMap {
	private Map<Point, Set<Entity>> map = new HashMap<Point, Set<Entity>>();
	
	void add(Entity entity) {
		Point location = entity.getLocation();
		Set<Entity> mapLocation = map.get(location);
		if(mapLocation == null) {
			mapLocation = new HashSet<Entity>();
			map.put(location, mapLocation);
		}
		mapLocation.add(entity);
	}
	
	void remove(Entity entity) {
		Point location = entity.getLocation();
		Set<Entity> mapLocation = map.get(location);
		mapLocation.remove(entity);
		if(mapLocation.isEmpty())
			map.remove(location);
	}
}

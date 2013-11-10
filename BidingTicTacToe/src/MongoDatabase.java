import com.mongodb.*;

public class MongoDatabase {

	private static MongoDatabase _instance;

	private Mongo _mongo;
	
	private DB _mongoDb;

	private final String _hardTable = "hardTable";
	
	private final String _easyTable = "easyTable";
	
	private final String _hardTag = "hrd";
	
	private final String _easyTag = "eay";
	
	public static MongoDatabase getDb() {
		try {
			if (_instance == null) {
				_instance = new MongoDatabase();
				_instance._mongo = new Mongo("localhost");
			}
			
			if(_instance._mongoDb == null || !_instance._mongoDb.getName().equals(SettingHelper.getInstance().loadPlayerName())) {
				//User has changed - reload the correct database.
				_instance._mongoDb = _instance._mongo.getDB(SettingHelper.getInstance().loadPlayerName());				
			} 
			return _instance;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}	
	
	public void addWinHard() {
		DBCollection col = _mongoDb.getCollection(_hardTable);
		col.insert(new BasicDBObject(_hardTag, _hardTag));		
	}
	
	public void addLoseHard() {
		DBCollection col = _mongoDb.getCollection(_hardTable);
		col.insert(new BasicDBObject("", ""));
	}
	
	public void addWinEasy() {
		DBCollection col = _mongoDb.getCollection(_easyTable);
		col.insert(new BasicDBObject(_easyTag, _easyTag));
	}
	
	public void addLoseEasy() {
		DBCollection col = _mongoDb.getCollection(_easyTable);
		col.insert(new BasicDBObject("", ""));
	}
	
	public long getHardGamesLose() {
		BasicDBObject query = new BasicDBObject(_hardTag, _hardTag);
		DBCollection col = _mongoDb.getCollection(_hardTable);
		int wins = col.find(query).count();
		return col.getCount() - wins;
	}
	
	public long getHardGamesWin() {
		BasicDBObject query = new BasicDBObject(_hardTag, _hardTag);
		DBCollection col = _mongoDb.getCollection(_hardTable);
		return col.find(query).count();		
	}
	
	public long getEasyGamesLose() {
		BasicDBObject query = new BasicDBObject(_easyTag, _easyTag);
		DBCollection col = _mongoDb.getCollection(_easyTable);
		int wins = col.find(query).count();
		return col.getCount() - wins;
	}
	
	public long getEasyGamesWin() {
		BasicDBObject query = new BasicDBObject(_easyTag, _easyTag);
		DBCollection col = _mongoDb.getCollection(_easyTable);
		return col.find(query).count();	
	}
}
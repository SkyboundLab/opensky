# OpenEly Configuration

## Setting up the Drasl API Bearer Token

To enable fetching the list of override UUIDs from the Drasl API, you need to configure the Bearer token:

1. Open the file: `shared/src/main/java/xyz/kaydax/openely/shared/API/DraslAPI.java`

2. Locate the following line:
   ```java
   private static final String BEARER_TOKEN = "YOUR_BEARER_TOKEN_HERE";
   ```

3. Replace `YOUR_BEARER_TOKEN_HERE` with your actual Bearer token from the Drasl API.

## How it works

The project now automatically fetches the list of player UUIDs from the Drasl API endpoint:
- **Endpoint**: `https://drasl.twint.my.id/drasl/api/v2/players`
- **Authentication**: Bearer token (configured in `DraslAPI.java`)
- **Caching**: Results are cached for 5 minutes to reduce API calls

When a player joins, the system:
1. Fetches the list of UUIDs from Drasl API (if not cached)
2. Checks if the player's UUID is in the override list
3. If the UUID matches OR if the player has "ely" or "drasl" properties, it modifies their skin

## Files Modified/Created

- **New Files**:
  - `shared/src/main/java/xyz/kaydax/openely/shared/API/DraslAPI.java` - API client for Drasl
  - `shared/src/main/java/xyz/kaydax/openely/shared/Player/DraslPlayer.java` - Model for Drasl player data
  
- **Modified Files**:
  - `shared/src/main/java/xyz/kaydax/openely/shared/OpenEly.java` - Updated to use dynamic UUID list
  - `shared/src/main/java/xyz/kaydax/openely/shared/Util/RequestUtil.java` - Added Bearer token support

## Testing

The system will log debug information when:
- Fetching UUIDs from the API
- Using cached UUID data
- Detecting override UUIDs
- Processing skin modifications

Monitor your server logs for `[DEBUG]` and `[ERROR]` messages to troubleshoot any issues.

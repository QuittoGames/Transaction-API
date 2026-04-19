package quitto.FinaceSysthen.Services.Auth;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private Set<UUID> approvedKeys = ConcurrentHashMap.newKeySet();

    public Set<UUID> getAproveadKeys() {
        return approvedKeys;
    }
    
    public boolean addArpoveadKeys(UUID idempotencyKey) throws RuntimeException{
        try{
            if (idempotencyKey == null){
                throw new RuntimeException("idempotencyKey is Null");
            }
            return approvedKeys.add(idempotencyKey);
        }
        catch (RuntimeException RE){
            logger.error("[ERROR] Values is null, Error: {}", RE.getMessage(), RE);
            return false;
        }

    }

    public boolean isValidKey(UUID key){
        return this.approvedKeys.contains(key);
    } 
}

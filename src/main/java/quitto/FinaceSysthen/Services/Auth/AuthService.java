package quitto.FinaceSysthen.Services.Auth;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

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
            System.out.println("[ERROR] Values is null, Error: " + RE.getMessage());
            return false;
        }

    }

    public boolean isValidKey(UUID key){
        return this.approvedKeys.contains(key);
    } 
}

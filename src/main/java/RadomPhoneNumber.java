import java.util.Random;

/**
 * @author yangqf
 * @version 1.0 2017/1/4
 */
public class RadomPhoneNumber{
    public static void main(String[] args){
        String[] starts = {"135", "187", "155", "138"};
        Random random = new Random();
//        for(int v = 0 ; v < 100; v ++){
            int i = random.nextInt(starts.length);
            StringBuilder sb = new StringBuilder();
            sb.append(starts[i]);
            for(int j = 0; j < 8; j++){
                sb.append(random.nextInt(10));
            }

            System.out.println(sb.toString());
//        }
    }
}

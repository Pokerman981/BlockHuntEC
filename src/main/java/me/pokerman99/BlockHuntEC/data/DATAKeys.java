package me.pokerman99.BlockHuntEC.data;

import com.google.common.reflect.TypeToken;
import javax.annotation.Generated;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.ListValue;

import java.util.List;

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-09-01T19:30:04.744Z")
public class DATAKeys {

    public DATAKeys() {}

    public final static Key<ListValue<String>> BLOCKHUNTDATA;
    static {
        TypeToken<List<String>> listStringToken = new TypeToken<List<String>>(){};
        TypeToken<ListValue<String>> listValueStringToken = new TypeToken<ListValue<String>>(){};
        BLOCKHUNTDATA = KeyFactory.makeListKey(listStringToken, listValueStringToken, DataQuery.of("Blockhuntdata"), "blockhuntec:blockhuntdata", "Blockhuntdata");
    }
}

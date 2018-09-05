package me.pokerman99.BlockHuntEC.data;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.immutable.ImmutableListValue;
import org.spongepowered.api.data.value.mutable.ListValue;

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-09-01T19:30:04.693Z")
public class DATA extends AbstractData<DATA, DATA.Immutable> {

    public List<String> blockhuntdata;

    {
        registerGettersAndSetters();
    }

    public DATA() {
    }

    public DATA(List<String> blockhuntdata) {
        this.blockhuntdata = blockhuntdata;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(DATAKeys.BLOCKHUNTDATA, this::getBlockhuntdata);
        registerFieldSetter(DATAKeys.BLOCKHUNTDATA, this::setBlockhuntdata);
        registerKeyValue(DATAKeys.BLOCKHUNTDATA, this::blockhuntdata);
    }

    public List<String> getBlockhuntdata() {
        return blockhuntdata;
    }

    public void setBlockhuntdata(List<String> blockhuntdata) {
        this.blockhuntdata = blockhuntdata;
    }

    public ListValue<String> blockhuntdata() {
        return Sponge.getRegistry().getValueFactory().createListValue(DATAKeys.BLOCKHUNTDATA, blockhuntdata);
    }

    @Override
    public Optional<DATA> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(DATA.class).ifPresent(that -> {
                DATA data = overlap.merge(this, that);
                this.blockhuntdata = data.blockhuntdata;
        });
        return Optional.of(this);
    }

    @Override
    public Optional<DATA> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<DATA> from(DataView container) {
        container.getStringList(DATAKeys.BLOCKHUNTDATA.getQuery()).ifPresent(v -> blockhuntdata = v);
        return Optional.of(this);
    }

    @Override
    public DATA copy() {
        return new DATA(blockhuntdata);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(blockhuntdata);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(DATAKeys.BLOCKHUNTDATA.getQuery(), blockhuntdata);
    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-09-01T19:30:04.735Z")
    public static class Immutable extends AbstractImmutableData<Immutable, DATA> {

        public List<String> blockhuntdata;
        {
            registerGetters();
        }

        Immutable() {
        }

        Immutable(List<String> blockhuntdata) {
            this.blockhuntdata = blockhuntdata;
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(DATAKeys.BLOCKHUNTDATA, this::getBlockhuntdata);
            registerKeyValue(DATAKeys.BLOCKHUNTDATA, this::blockhuntdata);
        }

        public List<String> getBlockhuntdata() {
            return blockhuntdata;
        }

        public ImmutableListValue<String> blockhuntdata() {
            return Sponge.getRegistry().getValueFactory().createListValue(DATAKeys.BLOCKHUNTDATA, blockhuntdata).asImmutable();
        }

        @Override
        public DATA asMutable() {
            return new DATA(blockhuntdata);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer()
                    .set(DATAKeys.BLOCKHUNTDATA.getQuery(), blockhuntdata);
        }

    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2018-09-01T19:30:04.742Z")
    public static class Builder extends AbstractDataBuilder<DATA> implements DataManipulatorBuilder<DATA, Immutable> {

        public Builder() {
            super(DATA.class, 1);
        }

        @Override
        public DATA create() {
            return new DATA();
        }

        @Override
        public Optional<DATA> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<DATA> buildContent(DataView container) throws InvalidDataException {
            return create().from(container);
        }

    }
}

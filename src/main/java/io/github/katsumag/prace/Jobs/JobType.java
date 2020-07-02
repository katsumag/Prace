package io.github.katsumag.prace.Jobs;

public enum JobType {

    MINER("Miner"),
    BUILDER("Builder"),
    WOODCUTTER("WoodCutter"),
    NONE("None");

    String name;

    JobType(String type){
        this.name = type;
    }

    public String getName() {
        return name;
    }

    public static JobType fromString(String string){

        switch (string.toLowerCase()) {
            case "miner":
                return MINER;
            case "builder":
                return BUILDER;
            case "woodcutter":
                return WOODCUTTER;
            case "none":
                return NONE;
            default:
                return null;
        }
    }

}

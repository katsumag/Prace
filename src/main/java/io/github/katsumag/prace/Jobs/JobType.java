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
        if (string.equalsIgnoreCase("Miner")){
            return MINER;
        } else {
            if (string.equalsIgnoreCase("Builder")){
                return BUILDER;
            } else{
                if (string.equalsIgnoreCase("WoodCutter")){
                    return WOODCUTTER;
                } else{
                    return null;
                }
            }
        }
    }

}

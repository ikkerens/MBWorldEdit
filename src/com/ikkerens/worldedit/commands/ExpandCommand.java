package com.ikkerens.worldedit.commands;

import com.ikkerens.worldedit.WorldEditPlugin;
import com.ikkerens.worldedit.handlers.AbstractCommand;
import com.ikkerens.worldedit.model.Selection;
import com.ikkerens.worldedit.wand.Direction;
import com.mbserver.api.game.Location;
import com.mbserver.api.game.Player;

public class ExpandCommand extends AbstractCommand {

    public ExpandCommand( WorldEditPlugin plugin ) {
        super( plugin );
    }

    @Override
    protected void execute( String label, Player player, String[] args ) {
        if ( args.length != 2 ) {
            player.sendMessage( "Usage: /" + label + " <amount> <direction>" );
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt( args[ 0 ] );
        } catch ( NumberFormatException e ) {
            player.sendMessage( "That amount is invalid." );
            return;
        }

        Direction dir;
        try {
            dir = Direction.valueOf( args[ 1 ].toUpperCase() );
        } catch ( NumberFormatException e ) {
            player.sendMessage( "That direction is invalid." );
            return;
        }

        Selection sel = this.getSelection( player );
        if ( sel.isValid() ) {
            Location lowest = sel.getMinimumPosition();
            Location highest = sel.getMaximumPosition();

            switch( dir ) {
                case UP:
                case NORTH:
                case WEST:
                    dir.addToLocation( highest, amount );
                    break;
                case EAST:
                case SOUTH:
                case DOWN:
                    dir.addToLocation( lowest, amount );
                    break;
            }

            sel.setPositions( lowest, highest );
        } else
            player.sendMessage( "You need a valid selection to do this." );
    }

}
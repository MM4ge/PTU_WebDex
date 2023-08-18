package org.allenfulmer.ptuviewer.generator.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.PokeConstants;
import org.allenfulmer.ptuviewer.models.Type;
import org.allenfulmer.ptuviewer.util.PokeUtils;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class HtmlMove extends Move {
    private boolean stab;
    private String htmlDb;

    public HtmlMove(Move m, List<Type> types) {
        super(m.getName(), m.getType(), m.getFrequency(), m.getUses(), m.getAc(), m.getDb(), m.getMoveClass(), m.getRange(), m.getEffect());
        this.stab = (Objects.nonNull(types) && types.contains(getType()) &&
                (getMoveClass() == MoveClass.PHYSICAL || getMoveClass() == MoveClass.SPECIAL));

        int numDb = PokeUtils.convertDb(m.getDb(), stab);
        if (numDb == 0)
            htmlDb = PokeUtils.getNonEmpty(m.getDb());
        else
            htmlDb = (stab) ? PokeUtils.wrapHtml(Integer.toString(numDb), PokeConstants.STAB_HTML_HIGHLIGHT)
                    : Integer.toString(numDb);
    }

    @Override
    public String getAc() {
        return PokeUtils.getNonEmpty(super.getAc());
    }

    @Override
    public String getDb() {
        return htmlDb;
    }

    public boolean isStab() {
        return stab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HtmlMove htmlMove = (HtmlMove) o;
        return isStab() == htmlMove.isStab();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isStab());
    }
}

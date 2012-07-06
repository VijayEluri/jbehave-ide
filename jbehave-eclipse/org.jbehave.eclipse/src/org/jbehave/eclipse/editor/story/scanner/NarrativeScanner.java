package org.jbehave.eclipse.editor.story.scanner;

import org.eclipse.jface.text.rules.IToken;
import org.jbehave.eclipse.JBehaveProject;
import org.jbehave.eclipse.Keyword;
import org.jbehave.eclipse.jface.TextAttributeProvider;
import org.jbehave.eclipse.parser.StoryPart;
import org.jbehave.eclipse.step.LocalizedStepSupport;
import org.jbehave.eclipse.textstyle.TextStyle;

public class NarrativeScanner extends AbstractStoryPartBasedScanner {
    
    private IToken keywordToken;

    public NarrativeScanner(JBehaveProject jbehaveProject, TextAttributeProvider textAttributeProvider) {
        super(jbehaveProject, textAttributeProvider);
        initialize();
    }
    
    @Override
    protected void initialize() {
        super.initialize();
        setDefaultToken(newToken(TextStyle.NARRATIVE_DEFAULT));
        keywordToken = newToken(TextStyle.NARRATIVE_KEYWORD);
    }
    
    @Override
    protected boolean isPartAccepted(StoryPart part) {
        Keyword keyword = part.getPreferredKeyword();
        if(keyword.isNarrative()) {
            return true;
        }
        return false;
    }

    @Override
    protected void emitPart(StoryPart part) {
        LocalizedStepSupport localizedStepSupport = getLocalizedStepSupport();
        if(handleKeyword(part, localizedStepSupport.lNarrative(false)) //
                || handleKeyword(part, localizedStepSupport.lAsA(false)) //
                || handleKeyword(part, localizedStepSupport.lInOrderTo(false)) //
                || handleKeyword(part, localizedStepSupport.lIWantTo(false))) {
            // done!
        }
        else {
            emitCommentAware(getDefaultToken(), part.getOffset(), part.getContent());
        }
    }
    
    private boolean handleKeyword(StoryPart part, String kwString) {
        String content = part.getContent();
        int offset = part.getOffset();
        
        if(content.startsWith(kwString)) {
            emit(keywordToken, offset, kwString.length());
            offset += kwString.length();
            emitCommentAware(getDefaultToken(), offset, content.substring(kwString.length()));
            return true;
        }
        return false;
    }
}
